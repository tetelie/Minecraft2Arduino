const WebSocket = require('ws');

// Créer un serveur WebSocket sur le port 8080
const wss = new WebSocket.Server({ port: 8080 });

// Liste des clients connectés
let clients = [];

// Lorsqu'un client se connecte
wss.on('connection', (ws) => {
  // Ajouter le client à la liste des clients connectés
  clients.push(ws);
  const clientIp = ws._socket.remoteAddress;
  console.log(`Un client est connecté. IP du client : ${clientIp}`);

  // Quand un message est reçu du client
  ws.on('message', (message) => {
    console.log(`Message reçu de ${clientIp}:`, message);

    // Répondre à tous les clients connectés (diffusion)
    clients.forEach(client => {
      // On ne renvoie pas le message au client qui l'a envoyé
      if (client !== ws && client.readyState === WebSocket.OPEN) {
        client.send(message);  // Diffusion du message à tous les autres clients
      }
    });
  });

  // Quand un client se déconnecte
  ws.on('close', () => {
    // Retirer le client de la liste des clients connectés
    clients = clients.filter(client => client !== ws);
    console.log(`Le client avec IP ${clientIp} s'est déconnecté.`);
    
    // Optionnellement, informer les autres clients que ce client s'est déconnecté
    clients.forEach(client => {
      if (client.readyState === WebSocket.OPEN) {
        client.send(`Un client avec l'IP ${clientIp} s'est déconnecté.`);
      }
    });
  });

  // Quand une erreur survient sur la connexion WebSocket
  ws.on('error', (error) => {
    console.log(`Erreur WebSocket pour ${clientIp}:`, error);
  });
});

// Afficher un message quand le serveur est prêt
console.log('Serveur WebSocket en écoute sur le port 8080...');
